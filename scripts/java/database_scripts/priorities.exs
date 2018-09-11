alias Adellis.Sales.Priority
priorities = [ 
%Priority{code: "AOG", description: "AIRCRAFT ON GROUND"},
%Priority{code: "IMMEDIATE", description: "WITHIN 1-2 WEEKS"},
%Priority{code: "FLEXIBLE", description: "NO SPECIFIC TIME"}
]


Enum.map(priorities, fn priority -> Repo.insert!(priority) end)