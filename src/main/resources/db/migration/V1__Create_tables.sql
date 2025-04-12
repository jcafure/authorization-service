-- Tabela de grupos
CREATE TABLE groups (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Tabela de permissões
CREATE TABLE permissions (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE
);

-- Tabela de roles
CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255)
);

-- Tabela de usuários
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at TIMESTAMP,
    update_at TIMESTAMP
);

-- Associação Role ↔ Permission (N:N)
CREATE TABLE role_permissions (
    permission_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    PRIMARY KEY (permission_id, role_id),
    CONSTRAINT fk_role_permissions_permission
        FOREIGN KEY (permission_id) REFERENCES permissions(id),
    CONSTRAINT fk_role_permissions_role
        FOREIGN KEY (role_id) REFERENCES role(id)
);

-- Associação User ↔ Group ↔ Role
CREATE TABLE user_role_group (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    group_id INTEGER NOT NULL,
    role_id INTEGER NOT NULL,
    CONSTRAINT fk_user_role_group_user
        FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_user_role_group_group
        FOREIGN KEY (group_id) REFERENCES groups(id),
    CONSTRAINT fk_user_role_group_role
        FOREIGN KEY (role_id) REFERENCES role(id)
);
