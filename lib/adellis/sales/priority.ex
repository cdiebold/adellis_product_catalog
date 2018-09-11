defmodule Adellis.Sales.Priority do
  use Ecto.Schema
  import Ecto.Changeset

  schema "priorities" do
    field(:code, :string)
    field(:description, :string)
  end

  @doc false
  def changeset(priority, attrs) do
    priority
    |> cast(attrs, [:code, :description])
    |> validate_required([:code, :description])
  end
end
