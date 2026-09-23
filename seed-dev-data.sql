-- ============================================================
-- DevJob 화면·API 테스트용 회사/채용공고 샘플 데이터
--
-- 앱을 한 번 기동해 company, job 테이블을 만들고, 회원을 하나 이상 만든 뒤 실행한다.
-- seed.sql을 먼저 실행하면 ADMIN 회원을 공고 작성자로 사용한다.
-- 실행: docker exec -i devjob-db psql -U devjob -d devjob < seed-dev-data.sql
--
-- 회사명 + 공고명이 같은 데이터는 다시 넣지 않으므로 여러 번 실행해도 안전하다.
-- salary 단위: 만원
-- ============================================================

BEGIN;

INSERT INTO company (name, location)
SELECT name, location
FROM (
    VALUES
        ('네이버클라우드', '경기 성남시'),
        ('카카오페이', '경기 성남시'),
        ('토스', '서울 강남구'),
        ('당근', '서울 서초구'),
        ('우아한형제들', '서울 송파구'),
        ('리멤버', '서울 강남구'),
        ('쏘카', '제주 제주시'),
        ('마키나락스', '서울 강남구')
) AS seed(name, location)
WHERE NOT EXISTS (
    SELECT 1
    FROM company c
    WHERE c.name = seed.name
);

DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM member) THEN
        RAISE EXCEPTION '공고 시드에는 작성자 회원이 필요합니다. seed.sql을 먼저 실행하거나 회원을 생성하세요.';
    END IF;
END $$;

WITH job_seed(company_name, title, description, salary, status) AS (
    VALUES
        ('네이버클라우드', '백엔드 개발자 (Kubernetes 플랫폼)', 'Java·Spring Boot 기반의 클라우드 플랫폼 API를 개발하고 Kubernetes 환경을 운영합니다.', 6500, 'OPEN'),
        ('네이버클라우드', 'SRE 엔지니어', '대규모 서비스의 안정성과 관측 가능성을 높이고 장애 대응 자동화를 만듭니다.', 7000, 'OPEN'),
        ('카카오페이', '서버 개발자', '결제·정산 도메인의 확장 가능한 백엔드 서비스를 설계하고 개발합니다.', 6800, 'OPEN'),
        ('카카오페이', '데이터 엔지니어', 'Kafka와 Spark를 이용한 실시간 금융 데이터 파이프라인을 구축합니다.', 7200, 'OPEN'),
        ('토스', 'Frontend Engineer', 'React·TypeScript로 금융 경험을 더 쉽고 빠르게 만드는 웹 서비스를 개발합니다.', 6400, 'OPEN'),
        ('토스', 'Android Developer', 'Kotlin 기반 토스 앱의 핵심 금융 기능을 개발하고 사용자 경험을 개선합니다.', 6200, 'OPEN'),
        ('당근', '백엔드 개발자 - 지역광고', '지역 기반 광고 플랫폼의 API와 대용량 트래픽 처리 시스템을 개발합니다.', 6300, 'OPEN'),
        ('당근', 'iOS 개발자', 'Swift와 SwiftUI를 활용해 이웃 간 연결을 돕는 iOS 기능을 개발합니다.', 6100, 'OPEN'),
        ('우아한형제들', 'Backend Engineer - 주문', '배달 주문의 안정적인 처리와 확장성을 위한 Java 기반 서비스를 개발합니다.', 7000, 'OPEN'),
        ('우아한형제들', 'DevOps 엔지니어', 'CI/CD 파이프라인과 클라우드 인프라를 자동화하고 운영합니다.', 6900, 'OPEN'),
        ('리멤버', '풀스택 개발자', 'React와 Spring Boot로 비즈니스 네트워킹 서비스를 빠르게 개선합니다.', 6000, 'OPEN'),
        ('리멤버', 'QA 엔지니어', '웹·모바일 제품의 품질 전략을 수립하고 테스트 자동화를 구축합니다.', 5200, 'OPEN'),
        ('쏘카', '데이터 분석가', '모빌리티 이용 데이터를 분석해 서비스 지표와 실험 의사결정을 지원합니다.', 5600, 'OPEN'),
        ('쏘카', '서버 개발자 - 차량 IoT', '차량 상태와 이동 데이터를 처리하는 실시간 백엔드 시스템을 개발합니다.', 6400, 'OPEN'),
        ('마키나락스', 'ML Engineer', '산업 현장 데이터를 바탕으로 예측·최적화 머신러닝 모델을 제품화합니다.', 7500, 'OPEN'),
        ('마키나락스', 'MLOps 엔지니어', '모델 학습·배포·모니터링 파이프라인을 설계하고 운영합니다.', 7300, 'OPEN'),
        ('네이버클라우드', '인턴 - 플랫폼 개발', '클라우드 플랫폼 개발 업무를 경험할 인턴을 모집합니다.', 3500, 'CLOSED'),
        ('카카오페이', '보안 엔지니어', '금융 서비스의 보안 점검과 인증 체계 고도화를 담당합니다.', 7100, 'CLOSED')
), job_owner AS (
    SELECT id
    FROM member
    ORDER BY (role = 'ADMIN') DESC, id
    LIMIT 1
)
INSERT INTO job (company_id, registered_by, title, description, salary, status, created_at, updated_at)
SELECT c.id, owner.id, s.title, s.description, s.salary, s.status, NOW(), NOW()
FROM job_seed s
JOIN company c ON c.name = s.company_name
CROSS JOIN job_owner owner
WHERE NOT EXISTS (
    SELECT 1
    FROM job j
    WHERE j.company_id = c.id
      AND j.title = s.title
);

COMMIT;
