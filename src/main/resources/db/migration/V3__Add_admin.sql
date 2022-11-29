USE students_db;

INSERT INTO user_t (username, password, role_id)
VALUES ('admin', '$2a$10$qp5jwIc/.ELds0nCZ2q19ezTkAxcJ76suq0UYH80rKKwOByS9cbpC',
        (SELECT id FROM role_t WHERE role_name='ROLE_ADMIN'));