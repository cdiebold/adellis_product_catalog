defmodule Adellis.Catalog.PricingTest do
  use Adellis.DataCase

  alias Adellis.Catalog.Pricing

  test "Can construct Money type from an integer" do
    price = %Pricing{nsn: "1111111111", unit_issue: "dz", price: 10_045}
  end
end
