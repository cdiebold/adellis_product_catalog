defmodule Adellis.Sales.Quote do
  use Ecto.Schema
  import Ecto.Changeset

  schema "quotes" do
    field(:company_name, :string)
    field(:email_address, :string)
    field(:first_name, :string)
    field(:last_name, :string)
    field(:phone_number, :string)

    timestamps()
  end

  @doc false
  def changeset(quote, attrs) do
    quote
    |> cast(attrs, [:first_name, :last_name, :company_name, :phone_number, :email_address])
    |> validate_required([:first_name, :last_name, :company_name, :phone_number, :email_address])
  end
end
