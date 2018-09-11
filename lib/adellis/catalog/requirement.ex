defmodule Adellis.Catalog.Requirement do
  use Ecto.Schema
  import Ecto.Changeset

  schema "requirements" do
    field(:nsn, :string)
    field(:reply_one, :string)
    field(:reply_two, :string)
    field(:requirement, :string)
  end

  @doc false
  def changeset(requirement, attrs) do
    requirement
    |> cast(attrs, [:nsn, :requirement, :reply_one, :reply_two])
    |> validate_required([:nsn, :requirement, :reply_one])
  end
end
