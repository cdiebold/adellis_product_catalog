use Mix.Config

# We don't run a server during test. If one is required,
# you can enable the server option below.
config :adellis, AdellisWeb.Endpoint,
  http: [port: 4001],
  server: true

config :hound, driver: "phantomjs"
# Print only warnings and errors during test
config :logger, level: :warn

config :adellis, Adellis.Mailer, adapter: Bamboo.TestAdapter

# Configure your database
config :adellis, Adellis.Repo,
  adapter: Ecto.Adapters.Postgres,
  username: "postgres",
  password: "postgres",
  database: "adellis_test",
  hostname: "localhost",
  pool: Ecto.Adapters.SQL.Sandbox
