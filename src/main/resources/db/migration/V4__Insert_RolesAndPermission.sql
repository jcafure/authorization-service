--Relacionameno admin
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 1);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 2);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 3);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 4);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 5);
INSERT INTO role_permissions (role_id, permission_id) VALUES (1, 6);
--Relacionamento organizer
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 1);--CREATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 2);--update
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 3);--delete
INSERT INTO role_permissions (role_id, permission_id) VALUES (2, 6);--view
--Relacionamento donor
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 4);--DONATE
INSERT INTO role_permissions (role_id, permission_id) VALUES (3, 6);--view