defmodule AdellisWeb.QuoteController do
  use AdellisWeb, :controller
  alias Adellis.Sales

  def create(conn, %{"quote" => quote_params}) do
    case Sales.create_quote(quote_params) do
      {:ok, _quote} ->
        conn
        |> put_flash(:info, "Quote submitted successfully")
        |> redirect(to: page_path(conn, :index))

      {:error, changeset} ->
        conn
        |> put_view(AdellisWeb.ProductView)
        |> render(:index, changeset: changeset)
    end
  end
end
