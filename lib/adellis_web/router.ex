defmodule AdellisWeb.Router do
  use AdellisWeb, :router

  pipeline :browser do
    plug(:accepts, ["html"])
    plug(:fetch_session)
    plug(:fetch_flash)
    plug(:protect_from_forgery)
    plug(:put_secure_browser_headers)
  end

  pipeline :api do
    plug(:accepts, ["json"])
  end

  scope "/", AdellisWeb do
    # Use the default browser stack
    pipe_through(:browser)

    get("/", PageController, :index)
    get("/about", PageController, :about)
    get("/contact", PageController, :contact)

    get("/products", ProductController, :index)
    get("/products/:nsn", ProductController, :show)

    post("/quote", QuoteController, :create)
  end

  scope "/api", AdellisWeb, as: :api do
    pipe_through(:api)

    scope "/v1", V1, as: :v1 do
      post("/products", ProductController, :search)
      get("/products", ProductController, :index)
      get("/products/:part_number", ProductController, :show)
    end
  end

  # Other scopes may use custom stacks.
  # scope "/api", AdellisWeb do
  #   pipe_through :api
  # end
end
