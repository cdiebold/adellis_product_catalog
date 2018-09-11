defmodule Adellis.Catalog.Manufacturer do
  use Ecto.Schema
  import Ecto.Changeset

  schema "manufacturers" do
    field(:agency_code, :string)
    field(:name, :string)
    field(:part_number, :string)
  end

  @doc false
  def changeset(manufacturer, attrs) do
    manufacturer
    |> cast(attrs, [:part_number, :agency_code, :name])
    |> validate_required([:part_number, :name])
  end
end
