defmodule Adellis.Repo.Migrations.CreateDemilitarizations do
  use Ecto.Migration

  def change do
    create table(:demilitarizations, primary_key: false) do
      add :code, :string, primary_key: true
      add :description, :text
    end
  end
end
