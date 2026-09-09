-- ============================================================
--  DevJob S1 따라하기용 샘플 시드 데이터
--  - 회사·회원만 넣는다. 공고·지원은 따라하기에서 API(POST)로 만든다
--    (공고 스키마는 챕터마다 진화 — deadline, registered_by(3.5~ NOT NULL) — 하므로 시드에 박지 않음).
--  - 앱이 한 번 기동해 스키마(ddl-auto=update)를 만든 뒤 실행한다.
--  - 실행:  docker exec -i devjob-db psql -U devjob -d devjob < seed_s1.sql
--  - 회원 비밀번호 = BCrypt('password123')  (3장 로그인 따라하기용)
-- ============================================================

INSERT INTO company (name, location) VALUES
  ('데브잡', '서울'),
  ('테크스타트', '판교');

INSERT INTO member (email, password, role) VALUES
  ('admin@devjob.kr', '$2b$10$7f/u2N3rW.4pMIVjTZL30./2BsNXUdFqMkAqHi0rH3fsyfN5CbIoy', 'ADMIN'),
  ('user@devjob.kr',  '$2b$10$7f/u2N3rW.4pMIVjTZL30./2BsNXUdFqMkAqHi0rH3fsyfN5CbIoy', 'USER');
