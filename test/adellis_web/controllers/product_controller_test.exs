defmodule Adellis.ProductControllerTest do
  use AdellisWeb.ConnCase
  import Adellis.Factory

  # if this value changes, you need to update the paginate value in Adellis.Repo.ex
  @paginate_by 10
  setup %{conn: conn} do
    insert_list(100, :product)
    {:ok, conn: put_req_header(conn, "accept", "application/json")}
  end

  describe "when using json" do
    test "response is 200" do
      conn = get(conn, api_v1_product_path(conn, :index))
      assert json_response(conn, 200)
    end

    test "paginate entries on index", %{conn: conn} do
      conn = get(conn, api_v1_product_path(conn, :index))
      response = json_response(conn, 200)["data"]
      assert length(response) == @paginate_by
    end

    test "paginated properties are  correct" do
      conn = get(conn, api_v1_product_path(conn, :index))
      assert get_resp_header(conn, "total") == ["100"]
      assert get_resp_header(conn, "per-page") == ["#{@paginate_by}"]
      assert get_resp_header(conn, "page-number") == ["1"]
      assert get_resp_header(conn, "total-pages") == ["10"]
    end

    test "paginated links are valid" do
      conn = get(conn, api_v1_product_path(conn, :index))

      link_header =
        get_resp_header(conn, "link")
        |> hd
        |> ExLinkHeader.parse!()

      assert "2" == link_header.next.params.page
      assert "10" == link_header.last.params.page
      assert "1" == link_header.first.params.page
    end
  end
end
