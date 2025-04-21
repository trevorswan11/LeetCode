#include <vector>
#include <iostream>
#include <chrono>

using namespace std;

class Solution {
public:
	Solution(vector<int>& nums)
	{
		auto start = chrono::high_resolution_clock::now();
		cout << singleNumber(nums) << endl;
		auto end = chrono::high_resolution_clock::now();

		chrono::duration<double, milli> ms = end - start;
		cout << "Function took " << ms.count() << " ms.\n";
	}
private:
    int singleNumber(vector<int>& nums)
	{
		int running = 0;
        for (size_t i = 0; i < nums.size(); i++)
		{
			running ^= nums[i];
		}
		return running;
    }
};

int main(int argc, const char * argv[])
{
	vector<int> numbers;
	numbers.reserve(10);
	numbers = {1, 2, 3, 2, 1, 4, 3};
	Solution sol(numbers);
}
