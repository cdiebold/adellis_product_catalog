defmodule Adellis.Repo.Migrations.CreateRequirements do
  use Ecto.Migration

  def change do
    create table(:requirements) do
      add :nsn, :string
      add :requirement, :string
      add :reply_one, :string
      add :reply_two, :string

    end

  end
end
