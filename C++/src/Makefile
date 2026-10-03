CXX      ?= g++
CXXFLAGS ?= -std=c++17 -Wall -Wextra -O2
SRCS     := $(wildcard *.cpp)
OBJS     := $(SRCS:.cpp=.o)

shop: $(OBJS)
	$(CXX) $(CXXFLAGS) -o $@ $(OBJS)

%.o: %.cpp
	$(CXX) $(CXXFLAGS) -c $< -o $@

clean:
	rm -f $(OBJS) shop
