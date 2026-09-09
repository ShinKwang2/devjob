-- ============================================================
--  DevJob S1 2장 페이징·정렬 실습용 샘플 공고 (12건)
--  - company_id = 1 (seed.sql의 첫 회사 '데브잡')을 참조.
--    → seed.sql(회사·회원)을 먼저 넣은 뒤 이 파일을 실행한다.
--  - 2장 스키마 기준(registered_by 없음). 3.5장부터는 공고 시드에 작성자가 필요하므로
--    이 파일은 2장 전용이다.
--  - 실행:  docker exec -i devjob-db psql -U devjob -d devjob < seed-jobs.sql
-- ============================================================

INSERT INTO job (company_id, title, description, salary, status, created_at, updated_at) VALUES
  (1, '백엔드 개발자',        'Spring Boot 기반 백엔드',     6000, 'OPEN', NOW(), NOW()),
  (1, '프론트엔드 개발자',    'React 기반 프론트엔드',       5500, 'OPEN', NOW(), NOW()),
  (1, '풀스택 개발자',        'Node + React 풀스택',         6500, 'OPEN', NOW(), NOW()),
  (1, '데이터 엔지니어',      'Spark/Kafka 데이터 파이프라인', 7000, 'OPEN', NOW(), NOW()),
  (1, 'DevOps 엔지니어',      'Kubernetes 운영',             7200, 'OPEN', NOW(), NOW()),
  (1, '안드로이드 개발자',    'Kotlin 안드로이드',           5800, 'OPEN', NOW(), NOW()),
  (1, 'iOS 개발자',           'Swift iOS',                   5900, 'OPEN', NOW(), NOW()),
  (1, 'QA 엔지니어',          '테스트 자동화',               4800, 'OPEN', NOW(), NOW()),
  (1, '머신러닝 엔지니어',    '추천 시스템 모델링',          8000, 'OPEN', NOW(), NOW()),
  (1, '보안 엔지니어',        '클라우드 보안',               7500, 'OPEN', NOW(), NOW()),
  (1, '백엔드 신입 개발자',   'Java/Spring 신입',            4000, 'OPEN', NOW(), NOW()),
  (1, '백엔드 시니어 개발자', 'MSA 설계 리드',               9000, 'OPEN', NOW(), NOW());
