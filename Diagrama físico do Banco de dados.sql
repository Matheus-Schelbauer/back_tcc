/*criar database ProxInvest pelo dbeaver e abrir script sql para criar essas tabelas nele*/

CREATE TABLE "user" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "email" varchar(60),
  "password" varchar(30)
);

CREATE TABLE "wallet" (
  "id" integer PRIMARY KEY,
  "name" varchar(30),
  "description" varchar(200),
  "walletValue" float,
  "user_id" integer
);

CREATE TABLE "assetWallet" (
  "id" integer PRIMARY KEY,
  "ticketCode" char,
  "quantity" integer,
  "unitaryValue" float,
  "totalValue" float,
  "wallet_id" integer,
  "assetOriginal_id" integer
);

CREATE TABLE "assetOriginal" (
  "id" integer PRIMARY KEY,
  "ticketCode" char,
  "unitaryValue" float
);

ALTER TABLE "wallet" ADD FOREIGN KEY ("user_id") REFERENCES "user" ("id");

ALTER TABLE "assetWallet" ADD FOREIGN KEY ("wallet_id") REFERENCES "wallet" ("id");

ALTER TABLE "assetWallet" ADD FOREIGN KEY ("assetOriginal_id") REFERENCES "assetOriginal" ("id");
