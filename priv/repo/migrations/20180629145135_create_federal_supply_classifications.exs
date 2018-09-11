defmodule Adellis.Repo.Migrations.CreateFederalSupplyClassifications do
  use Ecto.Migration

  def change do
    create table(:federal_supply_classifications, primary_key: false) do
      add :id, :integer, primary_key: true
      add :name, :string
    end
  end
end
