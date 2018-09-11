defmodule Adellis.Repo.Migrations.CreatePriorities do
  use Ecto.Migration

  def change do
    create table(:priorities) do
      add :code, :string
      add :description, :string

    end

  end
end
