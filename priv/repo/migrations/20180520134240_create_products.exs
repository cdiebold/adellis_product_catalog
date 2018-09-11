defmodule Adellis.Repo.Migrations.CreateProducts do
  use Ecto.Migration

  def change do
    create table(:products) do
      add(:nsn, :string)
      add(:nsn_formatted, :string)
      add(:name, :string)
      add(:item_name_code, :integer)
      add(:type_of_item_identification_code, :string)
    end

    create(unique_index(:products, [:nsn]))
    create(unique_index(:products, [:nsn_formatted]))
  end
end
