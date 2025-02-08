class NumberContainers:

    def __init__(self):
        self.container = {}
        self.entries = {}

    def change(self, index: int, number: int) -> None:
        try:
            self.container[index] = number
            if number in self.entries.keys():
                curr = self.entries[number]
                self.entries[number] = index if index < curr else curr
            else:
                self.entries[number] = index
            if number not in self.container.values():
                self.entries.pop(number)
        except:
            return None

    def find(self, number: int) -> int:

        return self.entries[number] if number in self.entries.keys() else -1
    
