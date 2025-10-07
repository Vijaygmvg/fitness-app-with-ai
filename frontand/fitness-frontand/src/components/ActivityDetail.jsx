import React, { useEffect, useState } from 'react';
import { useParams, useLocation } from 'react-router-dom';
import { getActivityDetail } from '../service/api';

const parseRecommendation = (text) => {
  const sections = text.split(/\n\n/); // split into paragraphs
  return sections.map((section) => {
    const [title, ...contentParts] = section.split(':');
    const content = contentParts.join(':').trim();
    return {
      title: title.trim(),
      content: content,
    };
  });
};

const ActivityDetail = () => {
  const { id } = useParams();
  const location = useLocation();
  const activity = location.state?.activity;

  const [recommendationData, setRecommendationData] = useState(null);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchRecommendation = async () => {
      try {
        const response = await getActivityDetail(id);
        setRecommendationData(response.data);
      } catch (error) {
        console.error('Error fetching recommendation:', error);
      } finally {
        setLoading(false);
      }
    };

    fetchRecommendation();
  }, [id]);

  if (loading) {
    return <div className="text-center mt-10 text-lg text-gray-600">Loading activity details...</div>;
  }

  if (!recommendationData) {
    return <div className="text-center mt-10 text-red-500">No recommendation found.</div>;
  }

  const {
    type,
    recommendation,
    improvements,
    suggestions,
    safety,
    createdAt,
  } = recommendationData;

  const parsedRecommendation = parseRecommendation(recommendation);

  return (
    <div className="p-6 max-w-4xl mx-auto">
      {/* Header */}
      <h2 className="text-3xl font-bold mb-4 text-indigo-700">{type} Summary</h2>

      {/* Activity Summary Box */}
      <div className="bg-white shadow-md rounded-lg p-6 border border-gray-200 mb-6">
        <div className="max-w-3xl mx-auto p-6">
      <h2 className="text-3xl font-bold text-indigo-700 mb-6">Activity Details</h2>

      <div className="bg-white shadow-md rounded-md border border-gray-200 p-6 space-y-4">
        <p><span className="font-semibold text-gray-700">Type:</span> {activity.type}</p>
        <p><span className="font-semibold text-gray-700">Duration:</span> {activity.duration} minutes</p>
        <p><span className="font-semibold text-gray-700">Calories Burned:</span> {activity.caloriesBurn} kcal</p>
        <p><span className="font-semibold text-gray-700">Start Time:</span> {new Date(activity.startTime).toLocaleString()}</p>
        <p><span className="font-semibold text-gray-700">Created At:</span> {new Date(activity.createdAt).toLocaleString()}</p>
        <p><span className="font-semibold text-gray-700">Updated At:</span> {new Date(activity.updatedAt).toLocaleString()}</p>
       
      </div>
    </div>
      </div>

      {/* Recommendation Section */}
      <div className="bg-indigo-50 p-6 rounded-md border border-indigo-300 mb-6">
        <h3 className="text-2xl font-semibold mb-4 text-indigo-800">AI Recommendation</h3>
        {parsedRecommendation.map((item, index) => (
          <div key={index} className="mb-4">
            <h4 className="text-lg font-bold text-indigo-600 capitalize">{item.title}</h4>
            <p className="text-gray-800 whitespace-pre-line">{item.content}</p>
          </div>
        ))}
      </div>

      {/* Improvements */}
      {improvements && improvements.length > 0 && (
        <div className="bg-white p-6 rounded-md border mb-6">
          <h3 className="text-xl font-semibold mb-2 text-green-700">Areas of Improvement</h3>
          <ul className="list-disc list-inside text-gray-800">
            {improvements.map((imp, i) => (
              <li key={i}>{imp}</li>
            ))}
          </ul>
        </div>
      )}

      {/* Suggestions */}
      {suggestions && suggestions.length > 0 && (
        <div className="bg-white p-6 rounded-md border mb-6">
          <h3 className="text-xl font-semibold mb-2 text-blue-700">Suggestions</h3>
          <ul className="list-disc list-inside text-gray-800 space-y-2">
            {suggestions.map((sug, i) => (
              <li key={i}>{sug}</li>
            ))}
          </ul>
        </div>
      )}

      {/* Safety Tips */}
      {safety && safety.length > 0 && (
        <div className="bg-white p-6 rounded-md border">
          <h3 className="text-xl font-semibold mb-2 text-red-700">Safety Tips</h3>
          <ul className="list-disc list-inside text-gray-800 space-y-1">
            {safety.map((tip, i) => (
              <li key={i}>{tip}</li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
};

export default ActivityDetail;
