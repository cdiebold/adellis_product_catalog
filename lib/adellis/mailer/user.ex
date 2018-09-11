defmodule Adellis.Mailer.User do
  defstruct [:name, :email]
end

defimpl Bamboo.Formatter, for: Adellis.Mailer.User do
  def format_email_address(user, _opts) do
    {user.name, user.email}
  end
end
