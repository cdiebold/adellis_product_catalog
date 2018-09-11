defmodule Adellis.Repo.Migrations.CreateFederalSupplyGroups do
  use Ecto.Migration

  def change do
    create table(:federal_supply_groups, primary_key: false) do
      add :id, :integer, primary_key: true
      add :name, :string

    end

  end
end
