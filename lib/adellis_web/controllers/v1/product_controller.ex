defmodule AdellisWeb.V1.ProductController do
  use AdellisWeb, :controller
  alias Adellis.Catalog

  def index(conn, params) do
    page = Catalog.paginate(params)

    conn
    |> Scrivener.Headers.paginate(page)
    |> render("index.json", products: page.entries)
  end

  def search(conn, params) do
    products = Catalog.search_products(params)
    render(conn, "search.json", products: products)
  end
end
