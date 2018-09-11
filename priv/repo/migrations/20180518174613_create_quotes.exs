defmodule Adellis.Repo.Migrations.CreateQuotes do
  use Ecto.Migration

  def change do
    execute("CREATE EXTENSION IF NOT EXISTS citext")

    create table(:quotes) do
      add(:first_name, :citext)
      add(:last_name, :citext)
      add(:company_name, :citext)
      add(:phone_number, :string)
      add(:email_address, :citext)

      timestamps()
    end
  end
end
