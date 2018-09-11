defmodule Adellis.Catalog.Demilitarization do
  use Ecto.Schema
  import Ecto.Changeset

  @primary_key {:code, :string, autogenerate: false}
  schema "demilitarizations" do
    field(:description, :string)
  end

  @doc false
  def changeset(demilitarization, attrs) do
    demilitarization
    |> cast(attrs, [:code, :description])
    |> validate_required([:code, :description])
    |> validate_length(:code, max: 1)
  end
end
