defmodule AdellisWeb.ProductController do
  use AdellisWeb, :controller

  alias Adellis.Sales
  alias Adellis.Catalog

  def index(conn, _params) do
    changeset = Sales.build_quote()
    render(conn, "index.html", changeset: changeset)
  end

  # def show(conn, %{"nsn" => params}) do
  #   query = Catalog.get_product(params)
  #   render(conn, "show.html", product: query)
  # end
end
