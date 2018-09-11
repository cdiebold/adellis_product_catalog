defmodule Adellis.Catalog.ProductTest do
  use Adellis.DataCase

  alias Adellis.Catalog.Product

  @valid_attrs %{
    nsn: "1_234_001_111_111",
    nsn_formatted: "1234-00-1111111",
    name: "sleeve, spacer",
    item_name_code: 123_456,
    type_of_item_identification_code: "K"
  }
  @invalid_attrs %{}

  test "changeset with valid attributes" do
    changeset = Product.changeset(%Product{}, @valid_attrs)
    assert changeset.valid?
  end

  test "changeset with invalid attributes" do
    changeset = Product.changeset(%Product{}, @invalid_attrs)
    refute changeset.valid?
  end

  test "type of name identification code is no more than 3 characters long" do
    attrs = %{@valid_attrs | type_of_item_identification_code: "aaaaa"}
    changeset = Product.changeset(%Product{}, attrs)

    assert %{type_of_item_identification_code: ["should be at most 3 character(s)"]} =
             errors_on(changeset)
  end

  test "product name is at least 3 characters long" do
    attrs = %{@valid_attrs | name: String.duplicate("A", 2)}
    changeset = Product.changeset(%Product{}, attrs)
    assert %{name: ["should be at least 3 character(s)"]} = errors_on(changeset)
  end

  test "product name is no more than 250 characters long" do
    attrs = %{@valid_attrs | name: String.duplicate("A", 251)}
    changeset = Product.changeset(%Product{}, attrs)
    assert %{name: ["should be at most 250 character(s)"]} = errors_on(changeset)
  end
end
