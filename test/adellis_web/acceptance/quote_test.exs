defmodule AdellisWeb.Acceptance.QuoteTest do
  use Adellis.DataCase
  use Hound.Helpers

  hound_session()

  test "user can submit a quote with valid data" do
    navigate_to("/products")

    form = find_element(:id, "quote-form")

    find_within_element(form, :name, "quote[first_name]")
    |> fill_field("John")

    find_within_element(form, :name, "quote[last_name]")
    |> fill_field("Smith")

    find_within_element(form, :name, "quote[email_address]")
    |> fill_field("jsmith@example.com")

    find_within_element(form, :name, "quote[phone_number]")
    |> fill_field("321-555-5555")

    find_within_element(form, :name, "quote[company_name]")
    |> fill_field("Ideal Logistics LLC")

    # click on submit button
    find_within_element(form, :class, "btn")
    |> click()

    assert current_path() == "/"

    message =
      find_element(:class, "alert-success")
      |> visible_text()

    assert message =~ "Quote submitted successfully"
  end

  test "quote form shows error messages with invalid data" do
    navigate_to("/products")

    form = find_element(:id, "quote-form")
    # submit an empty form
    find_within_element(form, :class, "btn")
    |> click()

    # assert current_path() == "/products"

    message =
      find_element(:class, "alert-danger")
      |> visible_text()

    assert message =~ "Please check the form for errors"
  end
end
