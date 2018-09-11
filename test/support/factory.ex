defmodule Adellis.Factory do
  @moduledoc """
    This is a factory module to create Products for our catalog. 
  """
  use ExMachina.Ecto, repo: Adellis.Repo

  alias Adellis.Catalog.Product

  def product_factory do
    nsn = Enum.random(100..1_000_000_000)

    nsn_str = nsn |> to_string()

    %Product{
      nsn: nsn_str,
      # will generate name0, name1,....
      name: sequence("name"),
      nsn_formatted: nsn_str,
      type_of_item_identification_code: sequence("be1"),
      item_name_code: 12
    }
  end
end
