defmodule Adellis.MailerTest do
  use ExUnit.Case
  import Bamboo.Email
  alias Adellis.Mailer
  use Bamboo.Test
  alias Adellis.Mailer.User

  test "creating an email" do
    email =
      new_email
      |> to("cdiebold2012@gmail.com")
      |> from("sales@adellis.com")
      |> subject("Adellis Corporation Quote Receipt")
      |> html_body("<h1>Test email</h1><p>email body</p>")
      |> text_body("Test email\n\nemail body")

    assert email.to == "cdiebold2012@gmail.com"
    assert email.from == "sales@adellis.com"
    assert email.subject == "Adellis Corporation Quote Receipt"
    assert email.html_body =~ ~r/email/
    assert email.text_body =~ ~r/email/
  end

  test "can compose email from base email" do
    email =
      base_email
      |> to("cdiebold2012@gmail.com")
      |> html_body("<h1>Test email</h1><p>email body</p>")
      |> text_body("Test email\n\nemail body")

    assert email.to == "cdiebold2012@gmail.com"
    assert email.from == "sales@adellis.com"
    assert email.subject == "Adellis Corporation Quote Receipt"
    assert email.html_body =~ ~r/email/
    assert email.text_body =~ ~r/email/
  end

  test "sending an email" do
    email =
      base_email
      |> to("cdiebold2012@gmail.com")
      |> html_body("<h1>Test email</h1><p>email body</p>")
      |> text_body("Test email\n\nemail body")

    email |> Mailer.deliver_now()

    assert_delivered_email(email)
  end

  test "normalized emails" do
    josh = %User{name: "Josh Adams", email: "josh@gmail.com"}
    adam = %User{name: "Adam Greenfield", email: "agreenfield@gmail.com"}

    email =
      base_email
      |> to([josh, adam])
      |> html_body("<h1>Test email</h1><p>email body</p>")
      |> text_body("Test email\n\nemail body")

    assert email.to == [josh, adam]

    email = Bamboo.Mailer.normalize_addresses(email)

    assert email.to == [
             {"Josh Adams", "josh@gmail.com"},
             {"Adam Greenfield", "agreenfield@gmail.com"}
           ]

    email |> Mailer.deliver_now()
    assert_delivered_email(email)
  end

  def base_email do
    new_email
    |> from("sales@adellis.com")
    |> subject("Adellis Corporation Quote Receipt")
  end
end
