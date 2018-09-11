defmodule Adellis.Repo.Migrations.CreateShelfLives do
  use Ecto.Migration

  def change do
    create table(:shelf_lives) do
      add :code, :string
      add :life, :integer

    end

  end
end
