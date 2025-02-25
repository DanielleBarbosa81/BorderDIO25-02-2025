--changeset Danielle_Barbosa:202524022146
--comment: Create boards table
CREATE TABLE BOARDS (
  id BIGSERIAL PRIMARY KEY,
  name VARCHAR(255) NOT NULL
);

--rollback DROP TABLE BOARDS;
