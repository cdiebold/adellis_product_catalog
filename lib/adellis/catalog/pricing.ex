defmodule Adellis.Catalog.Pricing do
  use Ecto.Schema
  import Ecto.Changeset

  schema "pricings" do
    field(:aac, :string)
    field(:nsn, :string)
    field(:quantity_per_unit_pack, :string)
    field(:unit_issue, :string)
    field(:price, Money.Ecto.Type)
  end

  @doc false
  def changeset(pricing, attrs) do
    pricing
    |> cast(attrs, [:nsn, :unit_issue, :aac, :quantity_per_unit_pack, :price])
    |> validate_required([:nsn, :unit_issue, :price])
  end
end
