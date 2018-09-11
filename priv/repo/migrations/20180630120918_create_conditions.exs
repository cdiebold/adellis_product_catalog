defmodule Adellis.Repo.Migrations.CreateConditions do
  use Ecto.Migration

  def change do
    create table(:conditions) do
      add :code, :string
      add :description, :string
    end

  end
end
