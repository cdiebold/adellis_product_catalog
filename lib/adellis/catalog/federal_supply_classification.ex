defmodule Adellis.Catalog.FederalSupplyClassification do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:id, :integer, autogenerate: false}
  schema "federal_supply_classifications" do
    # field :id, :integer
    field(:name, :string)
  end

  @doc false
  def changeset(federal_supply_classification, attrs) do
    federal_supply_classification
    |> cast(attrs, [:id, :name])
    |> validate_required([:id, :name])
  end
end
