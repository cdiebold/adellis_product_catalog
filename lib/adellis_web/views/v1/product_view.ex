defmodule AdellisWeb.V1.ProductView do
  use AdellisWeb, :view

  def render("index.json", %{products: products}) do
    %{data: render_many(products, AdellisWeb.V1.ProductView, "product.json")}
  end

  def render("product.json", %{product: product}) do
    %{nsn: product.nsn, nsn_formatted: product.nsn_formatted, name: product.name}
  end

  def render("search.json", %{products: products}) do
    %{data: render_many(products, AdellisWeb.V1.ProductView, "search.json")}
  end

  def render("search.json", %{product: product}) do
    %{nsn: product.nsn, nsn_formatted: product.nsn_formatted, name: product.name}
  end
end
