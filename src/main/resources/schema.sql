CREATE TABLE users (
  id INTEGER IDENTITY(1,1) PRIMARY KEY,
  username VARCHAR (40) UNIQUE,
  name VARCHAR (35),
  last_name VARCHAR (35),
  email VARCHAR (50) UNIQUE,
  password VARCHAR (40) NOT NULL
);
