defmodule Adellis.Catalog.FederalSupplyGroup do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:id, :integer, autogenerate: false}
  schema "federal_supply_groups" do
    # field :id, :integer
    field(:name, :string)
  end

  @doc false
  def changeset(federal_supply_group, attrs) do
    federal_supply_group
    |> cast(attrs, [:id, :name])
    |> validate_required([:id, :name])
  end
end
