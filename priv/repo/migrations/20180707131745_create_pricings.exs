defmodule Adellis.Repo.Migrations.CreatePricings do
  use Ecto.Migration

  def change do
    create table(:pricings) do
      add :nsn, :string
      add :unit_issue, :string
      add :aac, :string
      add :quantity_per_unit_pack, :string
      add :price, :integer

    end

  end
end
