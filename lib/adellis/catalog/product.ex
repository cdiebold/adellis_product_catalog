defmodule Adellis.Catalog.Product do
  use Ecto.Schema
  import Ecto.Changeset

  schema "products" do
    field(:name, :string)
    field(:item_name_code, :integer)
    field(:nsn, :string)
    field(:nsn_formatted, :string)
    field(:type_of_item_identification_code, :string)
  end

  @doc false
  def changeset(product, attrs) do
    product
    |> cast(attrs, [
      :nsn,
      :nsn_formatted,
      :name,
      :item_name_code,
      :type_of_item_identification_code
    ])
    |> validate_required([
      :nsn,
      :nsn_formatted,
      :name,
      :item_name_code,
      :type_of_item_identification_code
    ])
    |> validate_length(:type_of_item_identification_code, max: 3)
    |> validate_length(:name, min: 3, max: 250)
  end
end
