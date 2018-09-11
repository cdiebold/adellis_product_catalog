defmodule Adellis.Catalog.DemilitarizationTest do
  use Adellis.DataCase

  alias Adellis.Catalog.Demilitarization

  @valid_attrs %{
    code: "A",
    description: "Some interesting description"
  }
  @invalid_attrs %{}

  test "changeset with valid attributes" do
    changeset = Demilitarization.changeset(%Demilitarization{}, @valid_attrs)
    assert changeset.valid?
  end

  test "changeset with invalid attributes" do
    changeset = Demilitarization.changeset(%Demilitarization{}, @invalid_attrs)
    refute changeset.valid?
  end

  test "code is no more than a single character in length" do
    attrs = %{@valid_attrs | code: "AAA"}
    changeset = Demilitarization.changeset(%Demilitarization{}, attrs)
    assert %{code: ["should be at most 1 character(s)"]} = errors_on(changeset)
  end

  @doc """
  Description is of type text in the database. This is to ensure we can have a description longer than
  255 characters.
  """
  test "description can be longer than 255 characters" do
    attrs = %{@valid_attrs | description: String.duplicate("a", 500)}
    changeset = Demilitarization.changeset(%Demilitarization{}, attrs)
    assert changeset.valid?
  end
end
