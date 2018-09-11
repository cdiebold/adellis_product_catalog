defmodule Adellis.Repo.Migrations.CreateManufacturers do
  use Ecto.Migration

  def change do
    create table(:manufacturers) do
      add :part_number, :string
      add :agency_code, :string
      add :name, :string
    end

  end
end
