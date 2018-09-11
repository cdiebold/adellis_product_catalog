defmodule Adellis.SalesTest do
  use Adellis.DataCase
  alias Adellis.Sales
  alias Adellis.Sales.Quote

  test "build_quote/0 returns a quote changeset" do
    assert %Ecto.Changeset{data: %Quote{}} = Sales.build_quote()
  end

  test "build_quote/1 returns a quote changeset with values applied" do
    attrs = %{"first_name" => "Chris"}
    changeset = Sales.build_quote(attrs)
    assert changeset.params == attrs
  end

  test "create_quote/1 returns a quote for valid data" do
    valid_attrs = %{
      "first_name" => "John",
      "last_name" => "Smith",
      "email_address" => "jsmith@example.com",
      "phone_number" => "321-555-5555",
      "company_name" => "Adellis Corporation"
    }

    assert {:ok, _quote} = Sales.create_quote(valid_attrs)
  end

  test "create_quote/1 returns a changeset for invalid data" do
    invalid_attrs = %{}
    assert {:error, %Ecto.Changeset{}} = Sales.create_quote(invalid_attrs)
  end
end
