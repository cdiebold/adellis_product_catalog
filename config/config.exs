# This file is responsible for configuring your application
# and its dependencies with the aid of the Mix.Config module.
#
# This configuration file is loaded before any dependency and
# is restricted to this project.
use Mix.Config

# General application configuration
config :adellis, ecto_repos: [Adellis.Repo]

config :adellis, Adellis.Mailer, adapter: Bamboo.TestAdapter

config :money,
  # this allows you to do Money.new(100)
  default_currency: :USD,
  # change the default thousands separator for Money.to_string
  separator: ".",
  # change the default decimal delimeter for Money.to_string
  delimiter: ",",
  # don’t display the currency symbol in Money.to_string
  symbol: false,
  # position the symbol
  symbol_on_right: false,
  # add a space between symbol and number
  symbol_space: false,
  # don’t display the remainder or the delimeter
  fractional_unit: false

# Configures the endpoint
config :adellis, AdellisWeb.Endpoint,
  url: [host: "localhost"],
  secret_key_base: "wpIcnCF1kUzjv6cIVhBbh5OwqtZVkRmC/KuH6uGARZsr/AdDwRQo7FxOb/E1IXQv",
  render_errors: [view: AdellisWeb.ErrorView, accepts: ~w(html json)],
  pubsub: [name: Adellis.PubSub, adapter: Phoenix.PubSub.PG2]

# Configures Elixir's Logger
config :logger, :console,
  format: "$time $metadata[$level] $message\n",
  metadata: [:user_id]

# Import environment specific config. This must remain at the bottom
# of this file so it overrides the configuration defined above.
import_config "#{Mix.env()}.exs"
