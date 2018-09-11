alias Adellis.Catalog.ShelfLife
shelf_lives = [ 
%ShelfLife{code: "A", life: 1},
%ShelfLife{code: "B", life: 2},
%ShelfLife{code: "C", life: 3},
%ShelfLife{code: "D", life: 4},
%ShelfLife{code: "E", life: 5},
%ShelfLife{code: "F", life: 6},
%ShelfLife{code: "G", life: 9},
%ShelfLife{code: "H", life: 12},
%ShelfLife{code: "J", life: 15},
%ShelfLife{code: "K", life: 18},
%ShelfLife{code: "L", life: 21},
%ShelfLife{code: "M", life: 24},
%ShelfLife{code: "N", life: 27},
%ShelfLife{code: "P", life: 30},
%ShelfLife{code: "Q", life: 36},
%ShelfLife{code: "R", life: 48},
%ShelfLife{code: "S", life: 50},
%ShelfLife{code: "I", life: 72},
%ShelfLife{code: "T", life: 84},
%ShelfLife{code: "U", life: 96},
%ShelfLife{code: "W", life: 120},
%ShelfLife{code: "Y", life: 180},
%ShelfLife{code: "Z", life: 240}
]


Enum.map(shelf_lives, fn shelf_life -> Repo.insert!(shelf_life) end)