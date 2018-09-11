defmodule Adellis.Sales.Condition do
  use Ecto.Schema
  import Ecto.Changeset

  schema "conditions" do
    field(:code, :string)
    field(:description, :string)
  end

  @doc false
  def changeset(condition, attrs) do
    condition
    |> cast(attrs, [:"code,", :description])
    |> validate_required([:"code,", :description])
    |> validate_length(:code, max: 2)
  end
end
