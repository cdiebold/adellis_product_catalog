defmodule Adellis.Sales do
  alias Adellis.Sales.Quote
  alias Adellis.Repo

  def build_quote(attrs \\ %{}) do
    %Quote{}
    |> Quote.changeset(attrs)
  end

  def create_quote(attrs) do
    attrs
    |> build_quote()
    |> Repo.insert()
  end
end
