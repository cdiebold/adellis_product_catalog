defmodule Adellis.Catalog.ShelfLife do
  use Ecto.Schema
  import Ecto.Changeset

  schema "shelf_lives" do
    field(:code, :string)
    field(:life, :integer)
  end

  @doc false
  def changeset(shelf_life, attrs) do
    shelf_life
    |> cast(attrs, [:code, :life])
    |> validate_required([:code, :life])
  end
end
