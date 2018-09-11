defmodule Adellis.Catalog do
  alias Adellis.Repo
  alias Adellis.Catalog.Product
  import Ecto.Query

  def list_products do
    Product |> Repo.all()
  end

  def paginate(params) do
    Product
    |> Repo.paginate(params)
  end

  def search_products(params) do
    search_term = get_in(params, ["query"])

    Product
    |> search(search_term)
    |> Repo.all()
  end

  defp search(query, search_term) do
    wildcard_search = "%#{search_term}%"

    from(
      product in query,
      where: ilike(product.name, ^wildcard_search),
      or_where: ilike(product.nsn, ^wildcard_search),
      or_where: ilike(product.nsn_formatted, ^wildcard_search)
    )
  end
end
