# Hadoop MapReduce Similarity Search
Compute pairwise document similarity over the Project Gutenberg corpus using a two-step MapReduce pipeline.

---

## Prerequisites
- Java 1.8+  
- Hadoop 2.x or 3.x  
- Maven 3.x  

---

## Build & Run

1. **Package**
   ```bash
   mvn clean package
   ```

2. **Run on Hadoop**
	```bash
	chmod +x run.sh
	./run.sh /path/to/gutenberg /path/to/output
	```
The script runs two jobs in sequence:
	TF job writes to output/tf
	Similarity job writes to output/similarity

---

## Approach
**Text Preprocessing**
- Strip Gutenberg headers and footers
- Convert text to lowercase
- Remove punctuation
- Filter stopwords

**Term Frequency (TF)**
1. Mapper emits <term@docId, 1>.
2. Reducer sums counts → <term@docId, tf>.

**Pairwise Similarity**
1. Mapper takes <term@docId, tf> and emits every other doc sharing that term → keys of type (docA, docB).
2. Reducer accumulates co‑occurrence counts → similarity score.

---

## Output
Each line in output/similarity/part-* is:
	docIdA    docIdB    <similarity_score>


