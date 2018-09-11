defmodule AdellisWeb.Acceptance.HomepageTest do
  use ExUnit.Case
  use Hound.Helpers

  hound_session()

  test "can navigate to product page using navbar" do
    navigate_to("/")

    find_element(:tag, "nav")
    |> find_within_element(:link_text, "Products")
    |> click()

    assert current_path() == "/products"
  end

  test "can navigate to product page from click on browse product button" do
    navigate_to("/")
    find_element(:class, "custom-btn") |> click()
    assert current_path() == "/products"
  end

  test "can navigate to the contact page using navbar" do
    navigate_to("/")

    find_element(:tag, "nav")
    |> find_within_element(:link_text, "Contact")
    |> click()

    assert current_path() == "/contact"
  end
end
